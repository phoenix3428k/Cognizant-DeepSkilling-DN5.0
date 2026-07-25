import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationService } from '../../services/notification.service';

@Component({
  selector: 'app-notification',
  imports: [CommonModule],
  templateUrl: './notification.html',
  styleUrl: './notification.css',
  // this creates a new, separate instance scoped to that component instead of sharing a single global instance
  providers: [NotificationService]
})
export class NotificationComponent {
  constructor(public notificationService: NotificationService) {}

  addNotification() {
    this.notificationService.addNotification('New Notification At ' + new Date().toLocaleTimeString());
  }
}
