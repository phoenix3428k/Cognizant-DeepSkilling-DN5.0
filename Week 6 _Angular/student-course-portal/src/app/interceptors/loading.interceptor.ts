import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { finalize } from 'rxjs';
import { LoadingService } from '../services/loading.service';

export const loadingInterceptor: HttpInterceptorFn = (req, next) => {
  const loadingService = inject(LoadingService);
  Promise.resolve().then(() => loadingService.show());
  return next(req).pipe(
    finalize(() => Promise.resolve().then(() => loadingService.hide()))
  );
};
