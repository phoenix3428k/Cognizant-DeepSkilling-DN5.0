import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { SimpleChanges, SimpleChange } from '@angular/core';
import { provideMockStore, MockStore } from '@ngrx/store/testing';
import { provideHttpClient } from '@angular/common/http';
import { CourseCard } from './course-card';
import { Course } from '../../models/course.model';

const mockCourse: Course = {
  id: 1,
  name: 'Angular',
  code: 'A01',
  credits: 3,
  gradeStatus: 'passed'
};

describe('CourseCard', () => {
  let component: CourseCard;
  let fixture: ComponentFixture<CourseCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CourseCard],
      providers: [
        provideMockStore({ initialState: { enrollment: { enrolledCourseIds: [] } } }),
        provideHttpClient()
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(CourseCard);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display the course name in h3', () => {
    component.course = mockCourse;
    fixture.detectChanges();
    const h3 = fixture.debugElement.query(By.css('h3')).nativeElement.textContent;
    expect(h3).toContain('Angular');
  });

  it('should emit enrollRequested with course id when enroll button is clicked', () => {
    component.course = mockCourse;
    fixture.detectChanges();
    spyOn(component.enrollRequested, 'emit');
    const buttons = fixture.debugElement.queryAll(By.css('button'));
    const enrollBtn = buttons[buttons.length - 1];
    enrollBtn.nativeElement.click();
    fixture.detectChanges();
    expect(component.enrollRequested.emit).toHaveBeenCalledWith(1);
  });

  it('should log previous and current course data on ngOnChanges', () => {
    spyOn(console, 'log');
    const changes: SimpleChanges = {
      course: new SimpleChange(null, mockCourse, true)
    };
    component.course = mockCourse;
    component.ngOnChanges(changes);
    expect(console.log).toHaveBeenCalledWith('Previous Course Data: ', null);
    expect(console.log).toHaveBeenCalledWith('Current Course Data: ', mockCourse);
  });
});
