import { useEffect, useState } from 'react'
import style from './HeaderMap.module.css'
import { getAddressFromCoords } from '../../../../utils/getAdressFromCoords'
import { formatDateTime } from '../../../../utils/formatDateTime'

interface adress {
   country: string
   city: string
   fullAddress: string
}

function HeaderMap() {
   const [adress, setAdress] = useState<adress | null>(null)
   const [currentDateTime, setCurrentDateTime] = useState<string>('')
   const [reload, setReload] = useState<number>(0)

   useEffect(() => {
      setCurrentDateTime(formatDateTime(new Date()))

      const intervalId = setInterval(() => {
         setCurrentDateTime(formatDateTime(new Date()))
         setReload(reload + 1)
      }, 60000)

      navigator.geolocation.getCurrentPosition(
         async (position) => {
            setAdress(
               await getAddressFromCoords(
                  position.coords.latitude,
                  position.coords.longitude
               )
            )
         },
         (error) => {
            console.error('Error getting location:', error)
         }
      )

      return () => clearInterval(intervalId)
   }, [])

   return (
      <header className={style.header}>
         <div className={style.headerContent}>
            <div className={style.headerTop}>
               <img
                  src="img/svg/row.svg"
                  alt="Стрелочка"
                  width="30px"
                  height="30px"
                  className={style.iconRow}
               />
               <h1 className={style.title}>Карта пыльцы</h1>
               <div className={style.icons}>
                  <div className={style.iconPlaceholder}>
                     <img
                        src="img/svg/aim.svg"
                        alt="цель"
                        width="26px"
                        height="26px"
                        className={style.icon}
                     />
                  </div>
                  <div className={style.iconPlaceholder}>
                     <img
                        src="img/svg/stack.svg"
                        alt="стак"
                        width="26px"
                        height="26px"
                        className={style.icon}
                     />
                  </div>
               </div>
            </div>
            <div className={style.headerBottom}>
               <div className={style.locationInfo}>
                  <p className={style.location}>
                     {adress
                        ? `${adress.country}, ${adress.city}`
                        : 'Геокоординаты недоступны'}
                  </p>
                  <p className={style.dateTime}>{currentDateTime}</p>
               </div>
               <div className={style.updateInfo}>
                  <p className={style.updateText}>Обновлено</p>
                  <p className={style.updateTime}>{reload} мин назад</p>
               </div>
            </div>
         </div>
      </header>
   )
}

export default HeaderMap
