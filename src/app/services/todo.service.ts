import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders  } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Todo } from '../models/todo.model';

@Injectable({
  providedIn: 'root'
})
export class TodoService {
  private baseUrl = 'http://localhost:9000/api/todos'; // Replace 'api/todos' with your backend API endpoint

  constructor(private http: HttpClient) {}

  getTodos(): Observable<Todo[]> {
    const token = this.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<Todo[]>(this.baseUrl, { headers });
  }

  getTodoByUserId(): Observable<Todo[]> {
    const token = this.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<Todo[]>(`${this.baseUrl}/user`, { headers });
  }

  getTodoById(id: string): Observable<Todo> {
    const token = this.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<Todo>(`${this.baseUrl}/${id}`, { headers });
  }

  createTodo(todo: Todo): Observable<Todo> {
    const token = this.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.post<Todo>(this.baseUrl, todo, { headers });
  }

  updateTodo(id: string, todo: Todo): Observable<Todo> {
    const token = this.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.put<Todo>(`${this.baseUrl}/${id}`, todo, { headers });
  }

  deleteTodo(id: string): Observable<void> {
    const token = this.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.delete<void>(`${this.baseUrl}/${id}`, { headers });
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }
}