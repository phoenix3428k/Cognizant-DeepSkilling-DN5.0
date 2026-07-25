import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class LoadingService {
  isLoading$ = new BehaviorSubject<boolean>(false);

  show(): void {
    this.isLoading$.next(true);
  }

  hide(): void {
    this.isLoading$.next(false);
  }
}
