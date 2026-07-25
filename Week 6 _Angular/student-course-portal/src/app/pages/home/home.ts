import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { CourseService } from '../../services/course.service';
import { CourseSummaryWidget } from '../../components/course-summary-widget/course-summary-widget';
import { NotificationComponent } from '../../components/notification/notification';
import { selectEnrolledIds } from '../../store/enrollment/enrollment.selectors';

@Component({
  selector: 'app-home',
  imports: [CommonModule, FormsModule, CourseSummaryWidget, NotificationComponent],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home implements OnInit, OnDestroy {
  portalName = "Student Course Portal";
  availableCoursesCount = 0;
  enrolledCount$: Observable<number>;

  constructor(private courseService: CourseService, private store: Store) {
    this.enrolledCount$ = this.store.select(selectEnrolledIds).pipe(
      map(ids => ids.length)
    );
  }

  ngOnInit(){
    this.courseService.getCourses().subscribe({
      next: courses => this.availableCoursesCount = courses.length
    });
    console.log("Home Component Initialized - Courses Loaded");
  }

  ngOnDestroy(){
    console.log("Home Component Destroyed");
  }
}
