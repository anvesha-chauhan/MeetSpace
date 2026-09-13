import React, { useState, useEffect } from 'react';
import axios from 'axios';
import RoomCard from '../components/RoomCard';
import { Search, Filter } from 'lucide-react';

export default function Dashboard() {
  const [rooms, setRooms] = useState([]);
  const [capacityFilter, setCapacityFilter] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchRooms();
  }, [capacityFilter]);

  const fetchRooms = async () => {
    try {
      setLoading(true);
      const apiUrl = import.meta.env.VITE_API_URL || 'http://localhost:8080';
      const url = capacityFilter 
        ? `${apiUrl}/api/rooms?minCapacity=${capacityFilter}` 
        : `${apiUrl}/api/rooms`;
      
      const response = await axios.get(url);
      setRooms(response.data);
    } catch (error) {
      console.error('Failed to fetch rooms', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '32px' }}>
        <div>
          <h1>Available Rooms</h1>
          <p style={{ color: 'var(--text-muted)', marginTop: '8px' }}>
            Find and book the perfect space for your next meeting.
          </p>
        </div>
        
        <div style={{ display: 'flex', gap: '16px', alignItems: 'center' }}>
          <div style={{ position: 'relative' }}>
            <Filter size={18} style={{ position: 'absolute', left: '12px', top: '14px', color: 'var(--text-muted)' }} />
            <select 
              className="form-input" 
              style={{ paddingLeft: '40px', width: '200px' }}
              value={capacityFilter}
              onChange={(e) => setCapacityFilter(e.target.value)}
            >
              <option value="">Any Capacity</option>
              <option value="4">4+ People</option>
              <option value="8">8+ People</option>
              <option value="20">20+ People (Boardroom)</option>
            </select>
          </div>
        </div>
      </div>

      {loading ? (
        <div style={{ textAlign: 'center', padding: '64px', color: 'var(--text-muted)' }}>
          Loading rooms...
        </div>
      ) : (
        <div className="room-grid">
          {rooms.map(room => (
            <RoomCard key={room.id} room={room} />
          ))}
          {rooms.length === 0 && (
            <div style={{ gridColumn: '1 / -1', textAlign: 'center', padding: '64px', color: 'var(--text-muted)' }}>
              No rooms found matching your criteria.
            </div>
          )}
        </div>
      )}
    </div>
  );
}
