import { Component, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { Course } from '../../models/course.model';
import { CreditLabelPipe } from '../../pipes/credit-label.pipe';
import { EnrollmentService } from '../../services/enrollment.service';
import { enrollInCourse, unenrollFromCourse } from '../../store/enrollment/enrollment.actions';
import { selectEnrolledIds } from '../../store/enrollment/enrollment.selectors';

@Component({
  selector: 'app-course-card',
  imports: [CommonModule, CreditLabelPipe],
  templateUrl: './course-card.html',
  styleUrl: './course-card.scss'
})
export class CourseCard implements OnChanges {
  @Input() course!: Course;
  @Output() enrollRequested = new EventEmitter<number>();

  isExpanded = false;
  enrolledIds$: Observable<number[]>;

  // getters keep templates clean by moving logic out of the HTML
  get cardClasses() {
    return {
      'card--enrolled': this.enrollmentService.isEnrolled(this.course?.id),
      'card--full': this.course?.credits >= 4,
      'expanded': this.isExpanded
    };
  }

  constructor(private enrollmentService: EnrollmentService, private store: Store) {
    this.enrolledIds$ = this.store.select(selectEnrolledIds);
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['course']) {
      const previous = changes['course'].previousValue;
      const current = changes['course'].currentValue;
      console.log("Previous Course Data: ", previous);
      console.log("Current Course Data: ", current);
    }
  }

  toggleDetails() {
    this.isExpanded = !this.isExpanded;
  }

  onEnroll() {
    if (this.enrollmentService.isEnrolled(this.course.id)) {
      this.enrollmentService.unenroll(this.course.id);
      this.store.dispatch(unenrollFromCourse({ courseId: this.course.id }));
    }
    else {
      this.enrollmentService.enroll(this.course.id);
      this.store.dispatch(enrollInCourse({ courseId: this.course.id }));
    }
    this.enrollRequested.emit(this.course.id);
  }
}
