import { useEffect, useState } from 'react'
import { FaBell } from 'react-icons/fa'
import { CiSun } from 'react-icons/ci'
import style from './HeaderHome.module.css'
import { getWeatherDescription } from '../../../../utils/weatherCodes'

interface weatherCard {
   town: string
   temp: string
   weather: string
   pollen: string
   source: string[]
}

function HeaderHome() {
   const BellIcon = FaBell as React.ComponentType
   const SunIcon = CiSun as React.ComponentType

   const [card, setCard] = useState<weatherCard | null>(null)

   useEffect(() => {
      navigator.geolocation.getCurrentPosition(
         (position) => {
            const lat = position.coords.latitude
            const lon = position.coords.longitude
            fetch(
               `https://api.open-meteo.com/v1/forecast?latitude=${lat}&longitude=${lon}&current_weather=true&timezone=auto` //&daily=temperature_2m_min&timezone=auto
            )
               .then((response) => {
                  if (!response.ok)
                     throw new Error('Network response was not ok')
                  return response.json()
               })
               .then((data) => {
                  console.log(data)
                  setCard({
                     town: data.timezone.split('/')[1], // Здесь можно использовать данные из ответа
                     temp: data.current_weather.temperature,
                     weather: getWeatherDescription(
                        data.current_weather.weathercode
                     ),
                     pollen: 'Низкий уровень пыльцы',
                     source: ['Берёза', 'Дуб'],
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
      <div className={style.header}>
         <div className={style.headerTitle}>
            <div className={style.headerTitleText}>
               {/* TODO заменить User на объект пользователя с полем name */}
               <h2 className={style.headerTitleTitle}>
                  Добро пожаловать, User
               </h2>
               <h6 className={style.headerTitleDescription}>
                  Сегодня хороший день
               </h6>
            </div>
            <div className={style.headerTitleIcon}>
               <BellIcon />
            </div>
         </div>

         {/* TODO заменить заменить всё на fetch с запросом api */}
         <div className={style.headerWeatherCard}>
            <div className={style.weatherCardLeft}>
               <div className={style.leftTown}>{card?.town}</div>

               <div className={style.leftTemperature}>{card?.temp}C</div>

               <div className={style.leftWeather}>{card?.weather}</div>
            </div>
            <div className={style.weatherCardRight}>
               <div className={style.rightWeatherIcon}>
                  <SunIcon />
               </div>
               <div className={style.rightPollen}>{card?.pollen}</div>
               <div className={style.rightSource}>
                  {card?.source.join(', ')}
               </div>
            </div>
         </div>
      </div>
   )
}

export default HeaderHome
