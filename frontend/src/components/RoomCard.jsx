import React, { useState } from 'react';
import { Users, MapPin, CheckCircle, XCircle } from 'lucide-react';
import axios from 'axios';
import { format, addHours, startOfHour } from 'date-fns';

export default function RoomCard({ room }) {
  const [isBooking, setIsBooking] = useState(false);
  const [selectedDate, setSelectedDate] = useState('');
  const [selectedTime, setSelectedTime] = useState('');
  const [status, setStatus] = useState(null);

  const handleBook = async () => {
    if (!selectedDate || !selectedTime) {
      setStatus({ type: 'error', message: 'Please select date and time' });
      return;
    }

    const startDateTime = new Date(`${selectedDate}T${selectedTime}`);
    const endDateTime = addHours(startDateTime, 1);

    try {
      const apiUrl = import.meta.env.VITE_API_URL || 'http://localhost:8080';
      await axios.post(`${apiUrl}/api/bookings`, {
        roomId: room.id,
        startTime: startDateTime.toISOString(),
        endTime: endDateTime.toISOString()
      });
      setStatus({ type: 'success', message: 'Room booked successfully! Synced to G-Calendar.' });
      setIsBooking(false);
    } catch (error) {
      setStatus({ type: 'error', message: error.response?.data?.message || 'Conflict detected. Slot not available.' });
    }
  };

  return (
    <div className="glass-panel" style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
        <h3>{room.name}</h3>
        <span style={{ 
          background: 'rgba(255, 255, 255, 0.1)', 
          padding: '4px 12px', 
          borderRadius: '20px', 
          fontSize: '0.8rem',
          color: 'var(--text-light)'
        }}>
          Capacity: {room.capacity}
        </span>
      </div>
      
      <div style={{ display: 'flex', alignItems: 'center', gap: '8px', color: 'var(--text-muted)' }}>
        <MapPin size={16} />
        <span style={{ fontSize: '0.9rem' }}>{room.location}</span>
      </div>
      
      <div style={{ fontSize: '0.9rem', color: 'var(--text-muted)' }}>
        <strong>Amenities:</strong> {room.amenities}
      </div>

      {status && (
        <div style={{ 
          padding: '12px', 
          borderRadius: '8px', 
          display: 'flex', 
          alignItems: 'center', 
          gap: '8px',
          background: status.type === 'error' ? 'rgba(239, 68, 68, 0.1)' : 'rgba(34, 197, 94, 0.1)',
          color: status.type === 'error' ? '#ef4444' : '#22c55e',
          fontSize: '0.9rem'
        }}>
          {status.type === 'error' ? <XCircle size={18}/> : <CheckCircle size={18}/>}
          {status.message}
        </div>
      )}

      {isBooking ? (
        <div style={{ marginTop: '16px', display: 'flex', flexDirection: 'column', gap: '12px' }}>
          <div>
            <label className="form-label">Select Date</label>
            <input 
              type="date" 
              className="form-input" 
              value={selectedDate}
              onChange={(e) => setSelectedDate(e.target.value)}
              min={format(new Date(), 'yyyy-MM-dd')}
            />
          </div>
          <div>
            <label className="form-label">Select Start Time (1 Hour Slot)</label>
            <input 
              type="time" 
              className="form-input" 
              value={selectedTime}
              onChange={(e) => setSelectedTime(e.target.value)}
              step="3600"
            />
          </div>
          <div style={{ display: 'flex', gap: '8px', marginTop: '8px' }}>
            <button className="btn btn-primary" style={{ flex: 1 }} onClick={handleBook}>Confirm</button>
            <button className="btn btn-outline" style={{ flex: 1 }} onClick={() => setIsBooking(false)}>Cancel</button>
          </div>
        </div>
      ) : (
        <button 
          className="btn btn-primary" 
          style={{ marginTop: 'auto' }}
          onClick={() => setIsBooking(true)}
        >
          Book Room
        </button>
      )}
    </div>
  );
}
