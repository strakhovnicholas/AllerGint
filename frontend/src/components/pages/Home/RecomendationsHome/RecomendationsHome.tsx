import { useEffect, useState } from 'react'
import style from './RecomendationsHome.module.css'

interface Joke {
   setup: string
   punchline: string
   // id: number
   type: string
}

function RecomendationsHome() {
   const [joke, setJoke] = useState<Joke | null>(null)

   useEffect(() => {
      fetch('https://official-joke-api.appspot.com/random_joke')
         .then((response) => response.json())
         .then((data) => {
            setJoke({
               setup: data.setup,
               punchline: data.punchline,
               // id: data.id,
               type: data.type,
            })
         })
         .catch((error) => console.error(error))
   }, [])

   return (
      <div className="card first">
         <div className="title">Рекомендации на сегодня</div>

         <div className={style.recomendations}>
            {/* {joke ? (
               <>
                  <p>{joke.setup}</p>
                  <p>{joke.punchline}</p>
               </>
            ) : (
               <p>Загрузка шутки...</p>
            )} */}
            Проветривайте квартиру - пыльцы нет, но нужен свежий воздух<br></br>
            Делайте влажную уборку - актуально для домашней пыли и клещей
            <br></br>
            Чистите верхнюю одежду - на ней скапливаются уличные аллергены
            <br></br>
            Подготовьте аптечку к сезону - проверьте сроки антигистаминных
            <br></br>
         </div>
      </div>
   )
}

export default RecomendationsHome
