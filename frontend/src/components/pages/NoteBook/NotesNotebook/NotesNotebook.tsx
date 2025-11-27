import styles from './NotesNotebook.module.css'

function NotesNotebook() {
  return (
    <div className={`card ${styles.cardNotes}`}>
      <div className={styles.notesTitle}>
        <p className="title">Заметки</p>
      </div>
      <div className={styles.notesList}>
        <div className={styles.noteListItem}>
          <p>
            Lorem, ipsum dolor sit amet consectetur adipisicing elit.
            Repellendus quidem non magnam pariatur architecto perferendis
            cupiditate maxime, nostrum debitis dolore?
          </p>
          <div className={styles.noteListSub}>
            <p className={styles.noteSubtitle}>
              Добавлено в <span>saved time</span>
            </p>
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
