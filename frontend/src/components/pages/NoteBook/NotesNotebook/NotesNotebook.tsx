import { useEffect, useState } from 'react'
import styles from './NotesNotebook.module.css'
import { Day } from '../../../../interfaces/Notebook-interface'

function NotesNotebook() {
  const [day, setDay] = useState<Day | null>(null)

  useEffect(() => {
    fetch('http://localhost:8080/api/diary/day')
      .then((res) => res.json())
      .then((data) => setDay(data))
      .catch((err) => console.log(err))
  }, [])

  return (
    <div className={`card ${styles.cardNotes}`}>
      <div className={styles.notesTitle}>
        <p className="title">Заметки</p>
      </div>
      <div className={styles.notesList}>
        <div className={styles.noteListItem}>
          <div className="">
            <p>{day?.userNotes}</p>
            <div className={styles.noteListSub}>
              <p className={styles.noteSubtitle}>
                Добавлено в <span>saved time</span>
              </p>
            </div>
          </div>
        </div>
        <div className={styles.noteWrite}>
          <textarea
            className={styles.noteInput}
            name="keyboard"
            placeholder="Добавьте заметку о самочувствии, активности или других наблюдениях..."
          ></textarea>
          <button className={styles.noteAddBtn}>Добавить запись</button>
        </div>
      </div>
    </div>
  )
}

export default NotesNotebook
