import {useEffect, useState} from 'react';
import styles from './NotesNotebook.module.css';
import {Day} from '../../../../interfaces/Notebook-interface';

function NotesNotebook() {
    const [day, setDay] = useState<Day | null>(null);
    const [newNote, setNewNote] = useState('');
    const [saving, setSaving] = useState(false);

    useEffect(() => {
        const userId = 'b1d60e91-97e6-4659-ae7f-bde6808e2c4c';
        const timestamp = new Date().toISOString();

        fetch(`http://localhost:8080/api/diary/day?timestamp=${timestamp}`, {
            headers: {
                'X-User-Id': userId
            }
        })
            .then((res) => res.json())
            .then((data) => setDay(data))
            .catch((err) => console.error(err));
    }, []);

    const handleAddNote = () => {
        if (!newNote.trim() || !day) return;

        const userId = 'b1d60e91-97e6-4659-ae7f-bde6808e2c4c';
        setSaving(true);

        const updatedDay = {
            ...day,
            userNotes: day.userNotes ? `${day.userNotes}\n${newNote.trim()}` : newNote.trim()
        };

        fetch('http://localhost:8080/api/diary/e37b563d-6cd0-495f-8492-b244c89a993f', {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
                'X-User-Id': userId
            },
            body: JSON.stringify(updatedDay)
        })
            .then(res => res.json())
            .then(data => {
                setDay(data);
                setNewNote('');
            })
            .catch(err => console.error(err))
            .finally(() => setSaving(false));
    };

    return (
        <div className={`card ${styles.cardNotes}`}>
            <div className={styles.notesTitle}>
                <p className="title">Заметки</p>
            </div>

            <div className={styles.notesList}>
                <div className={styles.noteListItem}>
                    <p>{day?.userNotes || 'Нет записей'}</p>
                    {day?.timestamp && (
                        <div className={styles.noteListSub}>
                            <p className={styles.noteSubtitle}>
                                Добавлено в <span>{new Date(day.timestamp).toLocaleString()}</span>
                            </p>
                        </div>
                    )}
                </div>

                <div className={styles.noteWrite}>
                <textarea
                    className={styles.noteInput}
                    placeholder="Добавьте заметку о самочувствии, активности или других наблюдениях..."
                    value={newNote}
                    onChange={e => setNewNote(e.target.value)}
                ></textarea>
                    <button
                        className={styles.noteAddBtn}
                        onClick={handleAddNote}
                        disabled={saving}
                    >
                        {saving ? 'Сохраняем...' : 'Добавить запись'}
                    </button>
                </div>
            </div>
        </div>
    );
}

export default NotesNotebook;
