import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { tap } from 'rxjs/operators';
import { AuthResponse } from '../models/auth-response.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loggedIn = false;

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<AuthResponse> {
    // Replace the following with your actual login API call
    return this.http.post<AuthResponse>('http://localhost:9000/api/auth/login', { username, password }).pipe(
      tap(response => {
        if (response && response.token) {
          localStorage.setItem('token', response.token); // Store token in localStorage
          this.loggedIn = true;
        }
      })
    );
  }

  logout(): void {
    // Clear token from localStorage
    localStorage.removeItem('token');
    this.loggedIn = false;
  }

  isLoggedIn(): boolean {
    // Check if token exists in localStorage
    return !!localStorage.getItem('token');
  }
}