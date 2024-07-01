import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Task } from '../models/task.model';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private baseUrl = 'http://localhost:9000/api/tasks'; // Replace 'api/tasks' with your backend API endpoint

  constructor(private http: HttpClient) {}

  getTasks(): Observable<Task[]> {
    const token = this.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<Task[]>(this.baseUrl, { headers });
  }

  getTasksByTodoId(id: string): Observable<Task[]> {
    const token = this.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<Task[]>(`${this.baseUrl}/todo/${id}`, { headers });
  }

  getTaskById(id: string): Observable<Task> {
    const token = this.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<Task>(`${this.baseUrl}/${id}`, { headers });
  }

  createTask(task: Task): Observable<Task> {
    const token = this.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.post<Task>(this.baseUrl, task, { headers });
  }

  updateTask(id: string, task: Task): Observable<Task> {
    const token = this.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.put<Task>(`${this.baseUrl}/${id}`, task, { headers });
  }

  deleteTask(id: string): Observable<void> {
    const token = this.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.delete<void>(`${this.baseUrl}/${id}`, { headers });
  }
  
  getToken(): string | null {
    return localStorage.getItem('token');
  }
}