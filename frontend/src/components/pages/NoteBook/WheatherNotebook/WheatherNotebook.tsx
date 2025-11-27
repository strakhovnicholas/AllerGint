import { useEffect, useState } from 'react'
import styles from './WheatherNotebook.module.css'

interface weatherNb {
   temperature: string
   wind: number
   humidity: string
   pollen: string
}

function WheatherNotebook() {
   const [card, setCard] = useState<weatherNb | null>(null)

   useEffect(() => {
      navigator.geolocation.getCurrentPosition(
         (position) => {
            const lat = position.coords.latitude
            const lon = position.coords.longitude
            fetch(
               `https://api.open-meteo.com/v1/forecast?latitude=${lat}&longitude=${lon}&current_weather=true&hourly=relativehumidity_2m,windspeed_10m&timezone=auto`
            )
               .then((response) => {
                  if (!response.ok)
                     throw new Error('Network response was not ok')
                  return response.json()
               })
               .then((data) => {
                  setCard({
                     temperature: data.current_weather.temperature + 'C°',
                     wind: data.current_weather.windspeed,
                     humidity: data.hourly.relativehumidity_2m.pop(),
                     pollen: 'Низкая',
                  })
               })
               .catch((error) => console.error('Fetch error:', error))
         },
         (error) => {
            console.error('Geolocation error:', error)
         }
      )
   }, [])

   return (
      <div className={`card ${styles.wheatherCard}`}>
         <p className={styles.title}>Погодные условия</p>
         <div className={styles.wheatherCards}>
            <div className={styles.wheatherCardsItem}>
               <svg
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 640 640"
                  width="1.55rem"
               >
                  <path
                     fill="var(--blue-color)"
                     d="M320 64C267 64 224 107 224 160L224 324.7C194.5 351 176 389.4 176 432C176 511.5 240.5 576 320 576C399.5 576 464 511.5 464 432C464 389.4 445.5 351 416 324.7L416 160C416 107 373 64 320 64zM384 432C384 467.3 355.3 496 320 496C284.7 496 256 467.3 256 432C256 405.1 272.5 382.1 296 372.7L296 280C296 266.7 306.7 256 320 256C333.3 256 344 266.7 344 280L344 372.7C367.5 382.2 384 405.2 384 432z"
                  />
               </svg>
               <p className="mainSubTitles">температура</p>
               <p className={styles.wheatherCardsTitle}>{card?.temperature}</p>
            </div>
            <div className={styles.wheatherCardsItem}>
               <svg
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 640 640"
                  height="1.55rem"
               >
                  <path
                     fill="var(--blue-color)"
                     d="M320 96C239.2 96 174.5 132.8 127.4 176.6C80.6 220.1 49.3 272 34.4 307.7C31.1 315.6 31.1 324.4 34.4 332.3C49.3 368 80.6 420 127.4 463.4C174.5 507.1 239.2 544 320 544C400.8 544 465.5 507.2 512.6 463.4C559.4 419.9 590.7 368 605.6 332.3C608.9 324.4 608.9 315.6 605.6 307.7C590.7 272 559.4 220 512.6 176.6C465.5 132.9 400.8 96 320 96zM176 320C176 240.5 240.5 176 320 176C399.5 176 464 240.5 464 320C464 399.5 399.5 464 320 464C240.5 464 176 399.5 176 320zM320 256C320 291.3 291.3 320 256 320C244.5 320 233.7 317 224.3 311.6C223.3 322.5 224.2 333.7 227.2 344.8C240.9 396 293.6 426.4 344.8 412.7C396 399 426.4 346.3 412.7 295.1C400.5 249.4 357.2 220.3 311.6 224.3C316.9 233.6 320 244.4 320 256z"
                  />
               </svg>
               <p className="mainSubTitles">влажность</p>
               <p className={styles.wheatherCardsTitle}>{card?.humidity}%</p>
            </div>
            <div className={styles.wheatherCardsItem}>
               <svg
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 640 640"
                  height="1.55rem"
               >
                  <path
                     fill="var(--blue-color)"
                     d="M352 96C352 113.7 366.3 128 384 128L424 128C437.3 128 448 138.7 448 152C448 165.3 437.3 176 424 176L96 176C78.3 176 64 190.3 64 208C64 225.7 78.3 240 96 240L424 240C472.6 240 512 200.6 512 152C512 103.4 472.6 64 424 64L384 64C366.3 64 352 78.3 352 96zM416 448C416 465.7 430.3 480 448 480L480 480C533 480 576 437 576 384C576 331 533 288 480 288L96 288C78.3 288 64 302.3 64 320C64 337.7 78.3 352 96 352L480 352C497.7 352 512 366.3 512 384C512 401.7 497.7 416 480 416L448 416C430.3 416 416 430.3 416 448zM192 576L232 576C280.6 576 320 536.6 320 488C320 439.4 280.6 400 232 400L96 400C78.3 400 64 414.3 64 432C64 449.7 78.3 464 96 464L232 464C245.3 464 256 474.7 256 488C256 501.3 245.3 512 232 512L192 512C174.3 512 160 526.3 160 544C160 561.7 174.3 576 192 576z"
                  />
               </svg>
               <p className="mainSubTitles">ветер</p>
               <p className={styles.wheatherCardsTitle}>{card?.wind}км/ч</p>
            </div>
            <div className={styles.wheatherCardsItem}>
               <svg
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 640 640"
                  width="1.55rem"
               >
                  <path
                     fill="var(--blue-color)"
                     d="M576 96C576 204.1 499.4 294.3 397.6 315.4C389.7 257.3 363.6 205 325.1 164.5C365.2 104 433.9 64 512 64L544 64C561.7 64 576 78.3 576 96zM64 160C64 142.3 78.3 128 96 128L128 128C251.7 128 352 228.3 352 352L352 544C352 561.7 337.7 576 320 576C302.3 576 288 561.7 288 544L288 384C164.3 384 64 283.7 64 160z"
                  />
               </svg>
               <p className="mainSubTitles">пыльца</p>
               <p className={styles.wheatherCardsTitle}>Высокая</p>
            </div>
         </div>
      </div>
   )
}

export default WheatherNotebook
