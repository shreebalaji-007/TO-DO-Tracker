import { Component, EventEmitter, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Todo } from '../models/todo.model';
import { TodoService } from '../services/todo.service';
import { FormGroup, FormsModule, NgForm } from '@angular/forms';

@Component({
  selector: 'app-todo-dialog',
  templateUrl: './todo-dialog.component.html',
  styleUrls: ['./todo-dialog.component.scss']
})
export class TodoDialogComponent {
  closed: EventEmitter<void> = new EventEmitter<void>();

  constructor(
    public dialogRef: MatDialogRef<TodoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Todo,
    private todoService: TodoService
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  saveTodo(): void {
    if (this.data.id) {
      // If todo has an id, it means it's an existing todo being edited
      this.todoService.updateTodo(this.data.id, this.data).subscribe(() => {
        this.dialogRef.close();
        this.closed.emit();
      });
    } else {
      // If todo doesn't have an id, it means it's a new todo being added
      this.todoService.createTodo(this.data).subscribe(() => {
        this.dialogRef.close();
        this.closed.emit();
      });
    }
  }
}