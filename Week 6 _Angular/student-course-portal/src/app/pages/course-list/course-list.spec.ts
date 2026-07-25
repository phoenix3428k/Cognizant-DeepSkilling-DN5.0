import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideMockStore, MockStore } from '@ngrx/store/testing';
import { By } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { CourseList } from './course-list';
import { Course } from '../../models/course.model';

const mockCourses: Course[] = [
  { id: 1, name: 'Angular', code: 'A01', credits: 3, gradeStatus: 'passed' },
  { id: 2, name: 'Java', code: 'J02', credits: 4, gradeStatus: 'pending' }
];

describe('CourseList', () => {
  let component: CourseList;
  let fixture: ComponentFixture<CourseList>;
  let store: MockStore;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CourseList, RouterTestingModule],
      providers: [
        provideMockStore({
          initialState: {
            course: { courses: mockCourses, loading: false, error: null },
            enrollment: { enrolledCourseIds: [] }
          }
        }),
        { provide: ActivatedRoute, useValue: { queryParams: of({}) } },
        provideHttpClient()
      ]
    }).compileComponents();

    store = TestBed.inject(MockStore);
    fixture = TestBed.createComponent(CourseList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render course cards matching the store initial state', () => {
    component.isLoading = false;
    fixture.detectChanges();
    const cards = fixture.debugElement.queryAll(By.css('app-course-card'));
    expect(cards.length).toBe(2);
  });

  it('should show loading indicator when store loading state is true', () => {
    store.setState({
      course: { courses: [], loading: true, error: null },
      enrollment: { enrolledCourseIds: [] }
    });
    fixture.detectChanges();
    const loadingEl = fixture.debugElement.query(By.css('p'));
    expect(loadingEl.nativeElement.textContent).toContain('Loading Courses...');
  });
});
