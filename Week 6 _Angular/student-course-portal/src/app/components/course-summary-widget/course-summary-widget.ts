import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CourseService } from '../../services/course.service';

@Component({
  selector: 'app-course-summary-widget',
  imports: [CommonModule],
  templateUrl: './course-summary-widget.html',
  styleUrl: './course-summary-widget.css'
})
export class CourseSummaryWidget implements OnInit {
  courseCount = 0;

  constructor(private courseService: CourseService) {}

  ngOnInit() {
    this.courseService.getCourses().subscribe({
      next: courses => this.courseCount = courses.length
    });
  }
}
