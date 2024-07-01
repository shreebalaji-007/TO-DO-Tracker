import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { TaskService } from '../services/task.service';
import { Task } from '../models/task.model';
import { TaskDialogComponent } from '../task-dialog/task-dialog.component';
import { Observable, map } from 'rxjs';
import { Todo } from '../models/todo.model';
import { ActivatedRoute } from '@angular/router';
import { TodoService } from '../services/todo.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.scss']
})
export class TaskComponent implements OnInit {
  todoId!: string | null;
  todo!: Observable<Todo>;
  tasks!: Observable<Task[]>;
  displayedColumns: string[] = ['name', 'description', 'dueDate', 'priority', 'status', 'actions'];
  
  filteredTasks!: Observable<Task[]>;
  searchQuery: string = '';

  constructor(private taskService: TaskService, private dialog: MatDialog,
    private route: ActivatedRoute, private todoService: TodoService, private _snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.todoId = params.get('todoId'); // Get the todoId from route parameters
      this.getTodo(); // Load todo information based on todoId
      this.getTasks(); // Load tasks based on todoId
    });
  }

  getTasks(): void {
    if(this.todoId != null){
      this.tasks = this.taskService.getTasksByTodoId(this.todoId);
      this.filteredTasks = this.tasks;
    }
  }

  getTodo(): void {
    if(this.todoId != null){
      this.todo = this.todoService.getTodoById(this.todoId);
    }
  }

  addTaskDialog(task?: Task): void {
    const dialogRef = this.dialog.open(TaskDialogComponent, {
      width: '600px',
      data: { name: '', dueDate: new Date(), description: '', priority: '', status: '', todoId: this.todoId }
    });

    dialogRef.componentInstance.closed.subscribe(() => {
      this.openSnackBar("Saved successfully.");
      this.getTasks(); // Refresh todos after dialog is closed
    });
  }

  editTaskDialog(task: Task): void {
    const dialogRef = this.dialog.open(TaskDialogComponent, {
      width: '600px',
      data: {id: task.id, name: task.name, dueDate: task.dueDate, description: task.description, priority: task.priority, status: task.status, todoId: this.todoId }
    });

    dialogRef.componentInstance.closed.subscribe(() => {
      this.openSnackBar("Saved successfully.");
      this.getTasks(); // Refresh todos after dialog is closed
    });
  }

  deleteTask(task: Task): void {
    if (confirm('Are you sure you want to delete this task?')) {
      this.taskService.deleteTask(task.id).subscribe(() => {
        this.getTasks(); // Refresh tasks after deleting
      });
    }
  }

  filterTasks(): void {
    if (!this.searchQuery.trim()) {
      this.filteredTasks = this.tasks;
    } else {
      this.filteredTasks = this.tasks.pipe(
        map((tasks: any[]) => tasks.filter((task: { name: string; }) =>
          task.name.toLowerCase().includes(this.searchQuery.toLowerCase())
        ))
      );
    }
  }

  search(): void {
    this.filterTasks();
  }

  reset(): void {
    this.searchQuery = '';
    this.filterTasks();
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, "OK", {
      duration: 5000
    });
  }
}