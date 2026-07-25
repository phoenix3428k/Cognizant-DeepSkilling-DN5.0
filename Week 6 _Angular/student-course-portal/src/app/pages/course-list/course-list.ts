import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Store } from '@ngrx/store';
import { Observable, combineLatest } from 'rxjs';
import { map } from 'rxjs/operators';
import { CourseCard } from '../../components/course-card/course-card';
import { HighlightDirective } from '../../directives/highlight';
import { Course } from '../../models/course.model';
import { loadCourses } from '../../store/course/course.actions';
import { selectAllCourses, selectCoursesLoading, selectCoursesError } from '../../store/course/course.selectors';

@Component({
  selector: 'app-course-list',
  imports: [CommonModule, FormsModule, CourseCard, HighlightDirective],
  templateUrl: './course-list.html',
  styleUrl: './course-list.scss'
})
export class CourseList implements OnInit {
  selectedCourseId: number | null = null;
  searchTerm = '';
  isLoading = true;

  courses$: Observable<Course[]>;
  loading$: Observable<boolean>;
  error$: Observable<string | null>;

  constructor(private router: Router, private route: ActivatedRoute, private store: Store) {
    this.loading$ = this.store.select(selectCoursesLoading);
    this.error$ = this.store.select(selectCoursesError);

    this.courses$ = combineLatest([
      this.store.select(selectAllCourses),
      this.route.queryParams
    ]).pipe(
      map(([courses, params]) => {
        const search = params['search']?.toLowerCase() || '';
        this.searchTerm = search;
        if (!search) return courses;
        return courses.filter(c => 
          c.name.toLowerCase().includes(search) || 
          c.code.toLowerCase().includes(search)
        );
      })
    );
  }

  ngOnInit() {
    this.store.dispatch(loadCourses());
    setTimeout(() => {
      this.isLoading = false;
    }, 1500);
  }

  // trackBy improves performance because Angular reuses existing DOM elements instead of destroying and recreating them on every change
  trackByCourseId(index: number, course: Course): number {
    return course.id;
  }

  onEnroll(courseId: number) {
    console.log("Enrolling In Course: " + courseId);
    this.selectedCourseId = courseId;
  }

  onCardClick(courseId: number) {
    this.router.navigate(['courses', courseId]);
  }

  onSearchChange() {
    this.router.navigate(['courses'], { queryParams: { search: this.searchTerm } });
  }
}
