import React from 'react';
import { useAuth } from '../contexts/AuthContext';
import { LogOut, Calendar } from 'lucide-react';

export default function Navbar() {
  const { user, logout } = useAuth();

  return (
    <nav className="container navbar">
      <div className="logo" style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
        <Calendar color="var(--primary)" size={28} />
        <h2 className="gradient-text">MeetSpace</h2>
      </div>
      <div className="nav-links">
        <span className="nav-link">Welcome, {user?.name}</span>
        {user?.role === 'ROLE_ADMIN' && (
          <span className="nav-link" style={{ color: 'var(--secondary)' }}>Admin</span>
        )}
        <button className="btn btn-outline" onClick={logout} style={{ padding: '8px 16px' }}>
          <LogOut size={18} /> Logout
        </button>
      </div>
    </nav>
  );
}
