import { Component, OnInit } from '@angular/core';
import { TodoService } from '../services/todo.service';
import { Todo } from '../models/todo.model';
import { MatDialog } from '@angular/material/dialog';
import { TodoDialogComponent } from '../todo-dialog/todo-dialog.component';
import { Observable, map } from 'rxjs';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-todo',
  templateUrl: './todo.component.html',
  styleUrls: ['./todo.component.scss']
})
export class TodoComponent implements OnInit {
  todos!: Observable<Todo[]>;
  displayedColumns: string[] = ['title', 'description', 'actions'];
  filteredTodos!: Observable<Todo[]>;
  searchQuery: string = '';

  constructor(private todoService: TodoService, private dialog: MatDialog, private router: Router, private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.getTodos();
  }

  getTodos(): void {
    this.todos = this.todoService.getTodoByUserId();
    this.filteredTodos = this.todos;
  }

  addTodoDialog(): void {
    const dialogRef = this.dialog.open(TodoDialogComponent, {
      width: '600px',
      data: { title: '', description: '' }
    });

    dialogRef.componentInstance.closed.subscribe(() => {
      this.openSnackBar("Saved successfully.");
      this.getTodos(); // Refresh todos after dialog is closed
    });
  }

  editTodoDialog(todo: Todo): void {
    const dialogRef = this.dialog.open(TodoDialogComponent, {
      width: '600px',
      data: { id: todo.id, title: todo.title, description: todo.description }
    });

    dialogRef.componentInstance.closed.subscribe(() => {
      this.openSnackBar("Saved successfully.");
      this.getTodos(); // Refresh todos after dialog is closed
    });
  }

  deleteTodo(todo: Todo): void {
    if (confirm('Are you sure you want to delete this todo?')) {
      this.todoService.deleteTodo(todo.id).subscribe(() => {
        this.getTodos();
      });
    }
  }

  navigateToTasks(todoId: string): void {
    this.router.navigate(['/tasks', todoId]); // Navigate to TaskComponent with todoId as parameter
  }

  filterTodos(): void {
    if (!this.searchQuery.trim()) {
      this.filteredTodos = this.todos;
    } else {
      this.filteredTodos = this.todos.pipe(
        map((todos: any[]) => todos.filter((todo: { title: string; }) =>
          todo.title.toLowerCase().includes(this.searchQuery.toLowerCase())
        ))
      );
    }
  }

  search(): void {
    this.filterTodos();
  }

  reset(): void {
    this.searchQuery = '';
    this.filterTodos();
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, "OK", {
      duration: 5000
    });
  }
}