import { useEffect, useState } from 'react'
import style from './RecomendationsHome.module.css'

interface AiNotesDto {
  id: string
  aiNotes: string
}

const userId = 'b1d60e91-97e6-4659-ae7f-bde6808e2c4c'
const diaryPageId = 'e37b563d-6cd0-495f-8492-b244c89a993f'

function RecomendationsHome() {
  const [aiNotes, setAiNotes] = useState<AiNotesDto | null>(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    const fetchAiNotes = async () => {
      try {
        setLoading(true)
        setError(null)

        const res = await fetch(
          `http://localhost:8080/api/diary/${diaryPageId}/ai-notes`,
          {
            method: 'GET',
            headers: {
              'X-User-Id': userId
            }
          }
        )

        if (!res.ok) {
          throw new Error(`Error ${res.status}: ${res.statusText}`)
        }

        const data: AiNotesDto = await res.json()
        setAiNotes(data)
      } catch (err: any) {
        console.error(err)
        setError(err.message)
      } finally {
        setLoading(false)
      }
    }

    fetchAiNotes()
  }, [])

  return (
    <div className={style.recomendationsContainer}>
      <div className={style.header}>
        <div className={style.iconWrapper}>
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M12 2L2 7L12 12L22 7L12 2Z" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
            <path d="M2 17L12 22L22 17" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
            <path d="M2 12L12 17L22 12" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
          </svg>
        </div>
        <div className={style.headerText}>
          <h2 className={style.title}>Рекомендации</h2>
          <p className={style.subtitle}>на сегодня</p>
        </div>
      </div>

      <div className={style.recomendations}>
        {loading ? (
          <div className={style.loading}>Загрузка рекомендаций...</div>
        ) : error ? (
          <div className={style.error}>{error}</div>
        ) : aiNotes?.aiNotes ? (
          aiNotes.aiNotes.split('\n').filter(line => line.trim()).map((line, index) => (
            <div key={index} className={style.recomendationItem}>
              <div className={style.bullet}></div>
              <p>{line}</p>
            </div>
          ))
        ) : (
          <div className={style.empty}>Нет рекомендаций на сегодня</div>
        )}
      </div>
    </div>
  )
}

export default RecomendationsHome
