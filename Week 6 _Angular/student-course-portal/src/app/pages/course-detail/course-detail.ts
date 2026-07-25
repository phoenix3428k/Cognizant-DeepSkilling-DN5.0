import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Subject } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { CourseService } from '../../services/course.service';
import { EnrollmentService } from '../../services/enrollment.service';
import { Course } from '../../models/course.model';

@Component({
  selector: 'app-course-detail',
  imports: [CommonModule],
  templateUrl: './course-detail.html',
  styleUrl: './course-detail.css'
})
export class CourseDetail implements OnInit {
  course: Course | undefined;
  students: any[] = [];
  selectedCourseId$ = new Subject<number>();

  constructor(
    private route: ActivatedRoute,
    private courseService: CourseService,
    private enrollmentService: EnrollmentService
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.courseService.getCourseById(id).subscribe({
      next: course => this.course = course,
      error: err => console.error(err)
    });

    // switchMap cancels the previous inner Observable when a new courseId arrives, so only the latest course's students are loaded
    this.selectedCourseId$.pipe(
      switchMap(courseId => this.enrollmentService.getStudentsByCourse(courseId))
    ).subscribe(students => this.students = students);

    this.selectedCourseId$.next(id);
  }
}
