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
    <div className="card">
      <div className="title">Рекомендации на сегодня</div>

      <div className={style.recomendations}>
        {loading ? (
          <p>Загрузка рекомендаций...</p>
        ) : error ? (
          <p style={{ color: 'red' }}>{error}</p>
        ) : aiNotes?.aiNotes ? (
          aiNotes.aiNotes.split('\n').map((line, index) => <p key={index}>{line}</p>)
        ) : (
          <p>Нет рекомендаций на сегодня</p>
        )}
      </div>
    </div>
  )
}

export default RecomendationsHome
