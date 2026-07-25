import { Injectable } from '@angular/core';

@Injectable()
export class NotificationService {
  notifications: string[] = [];

  addNotification(message: string): void {
    this.notifications.push(message);
  }
}
