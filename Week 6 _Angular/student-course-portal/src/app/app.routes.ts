import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { CoursesLayout } from './pages/courses-layout/courses-layout';
import { CourseList } from './pages/course-list/course-list';
import { CourseDetail } from './pages/course-detail/course-detail';
import { StudentProfile } from './pages/student-profile/student-profile';
import { NotFound } from './pages/not-found/not-found';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', component: Home },
  {
    path: 'courses',
    component: CoursesLayout,
    children: [
      { path: '', component: CourseList },
      { path: ':id', component: CourseDetail }
    ]
  },
  { path: 'profile', canActivate: [authGuard], component: StudentProfile },
  {
    path: 'enroll',
    canActivate: [authGuard],
    loadChildren: () => import('./features/enrollment/enrollment.module').then(m => m.EnrollmentModule)
  },
  { path: '**', component: NotFound }
];
