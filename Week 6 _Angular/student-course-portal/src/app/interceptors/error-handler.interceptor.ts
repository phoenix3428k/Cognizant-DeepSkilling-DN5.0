import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const errorHandlerInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);
  return next(req).pipe(
    catchError(error => {
      if (error.status === 401) {
        router.navigate(['/']);
      }
      if (error.status === 500) {
        alert('A Server Error Occurred. Please Try Again Later');
      }
      return throwError(() => error);
    })
  );
};
