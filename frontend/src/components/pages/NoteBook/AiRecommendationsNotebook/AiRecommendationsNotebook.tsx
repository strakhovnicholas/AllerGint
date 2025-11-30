import { useEffect, useState } from 'react'
import styles from './AiRecommendationsNotebook.module.css'

interface DiaryPageDto {
  id: string
  userId: string
  healthState: string
  timestamp: string
  aiNotes: string
}

function AiRecommendationsNotebook() {
  const [page, setPage] = useState<DiaryPageDto | null>(null)
  const [loading, setLoading] = useState(true)
  const userId = 'b1d60e91-97e6-4659-ae7f-bde6808e2c4c'

  useEffect(() => {
    const today = new Date()
    const isoDate = today.toISOString()

    fetch(`http://localhost:8080/api/diary/day?timestamp=${isoDate}`, {
      headers: {
        'X-User-Id': userId,
      },
    })
      .then(res => res.json())
      .then(data => setPage(data))
      .catch(err => console.error('Ошибка при получении AI рекомендаций:', err))
      .finally(() => setLoading(false))
  }, [])

  if (loading) {
    return null
  }

  if (!page?.aiNotes) {
    return null
  }

  return (
    <div className={styles.aiRecommendationsContainer}>
      <div className={styles.header}>
        <div className={styles.iconWrapper}>
          <svg width="28" height="28" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M12 2L2 7L12 12L22 7L12 2Z" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
            <path d="M2 17L12 22L22 17" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
            <path d="M2 12L12 17L22 12" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
          </svg>
        </div>
        <div className={styles.headerText}>
          <h2 className={styles.title}>AI рекомендации</h2>
          <p className={styles.subtitle}>на сегодня</p>
        </div>
      </div>

      <div className={styles.recommendations}>
        {page.aiNotes.split('\n').filter(line => line.trim()).map((line, index) => (
          <div key={index} className={styles.recommendationItem}>
            <div className={styles.bullet}></div>
            <p>{line}</p>
          </div>
        ))}
      </div>
    </div>
  )
}

export default AiRecommendationsNotebook

