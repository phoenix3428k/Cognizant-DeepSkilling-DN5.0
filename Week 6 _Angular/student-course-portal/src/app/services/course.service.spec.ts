import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CourseService } from './course.service';
import { Course } from '../models/course.model';

const mockCourses: Course[] = [
  { id: 1, name: 'Angular', code: 'A01', credits: 3, gradeStatus: 'passed' },
  { id: 2, name: 'Java', code: 'J02', credits: 4, gradeStatus: 'pending' }
];

describe('CourseService', () => {
  let service: CourseService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CourseService]
    });
    service = TestBed.inject(CourseService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getCourses() should return list of courses', () => {
    service.getCourses().subscribe(courses => {
      expect(courses.length).toBe(2);
      expect(courses[0].name).toBe('Angular');
    });

    const req = httpMock.expectOne('http://localhost:3000/courses');
    expect(req.request.method).toBe('GET');
    req.flush(mockCourses);
  });

  it('getCourses() should propagate error on 500 response', () => {
    let errorMessage = '';

    service.getCourses().subscribe({
      next: () => fail('expected an error'),
      error: err => errorMessage = err.message
    });

    const req1 = httpMock.expectOne('http://localhost:3000/courses');
    req1.flush('Server Error', { status: 500, statusText: 'Internal Server Error' });

    const req2 = httpMock.expectOne('http://localhost:3000/courses');
    req2.flush('Server Error', { status: 500, statusText: 'Internal Server Error' });

    const req3 = httpMock.expectOne('http://localhost:3000/courses');
    req3.flush('Server Error', { status: 500, statusText: 'Internal Server Error' });
    expect(errorMessage).toBe('Failed to load courses. Please try again.');
  });
});
