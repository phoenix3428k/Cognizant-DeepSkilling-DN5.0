import { CanDeactivateFn } from '@angular/router';
import { ReactiveEnrollmentForm } from '../pages/reactive-enrollment-form/reactive-enrollment-form';

export const unsavedChangesGuard: CanDeactivateFn<ReactiveEnrollmentForm> = (component) => {
  if (component.enrollForm.dirty) {
    return window.confirm('You Have Unsaved Changes. Leave?');
  }
  return true;
};
