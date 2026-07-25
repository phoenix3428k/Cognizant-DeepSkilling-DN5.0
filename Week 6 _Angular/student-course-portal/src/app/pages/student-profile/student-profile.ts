import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EnrollmentService } from '../../services/enrollment.service';
import { Course } from '../../models/course.model';

@Component({
  selector: 'app-student-profile',
  imports: [CommonModule],
  templateUrl: './student-profile.html',
  styleUrl: './student-profile.scss'
})
export class StudentProfile implements OnInit {
  enrolledCourses: Course[] = [];

  constructor(private enrollmentService: EnrollmentService) {}

  ngOnInit() {
    this.enrollmentService.getEnrolledCourses().subscribe({
      next: courses => this.enrolledCourses = courses
    });
  }
}
