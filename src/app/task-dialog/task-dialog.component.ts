import { Component, EventEmitter, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Task } from '../models/task.model';
import { TaskService } from '../services/task.service';

@Component({
  selector: 'app-task-dialog',
  templateUrl: './task-dialog.component.html',
  styleUrls: ['./task-dialog.component.scss']
})
export class TaskDialogComponent {
  closed: EventEmitter<void> = new EventEmitter<void>();
  todayDate:Date = new Date();

  constructor(
    public dialogRef: MatDialogRef<TaskDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Task,
    private taskService: TaskService
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  saveTask(): void {
    if (this.data.id) {
      // If todo has an id, it means it's an existing todo being edited
      this.taskService.updateTask(this.data.id, this.data).subscribe(() => {
        this.dialogRef.close();
        this.closed.emit();
      });
    } else {
      // If todo doesn't have an id, it means it's a new todo being added
      this.taskService.createTask(this.data).subscribe(() => {
        this.dialogRef.close();
        this.closed.emit();
      });
    }
  }
}