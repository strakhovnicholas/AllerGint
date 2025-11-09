import { FaBell } from 'react-icons/fa'
import { CiSun } from 'react-icons/ci'
import style from './HeaderHome.module.css'

function HeaderHome() {
   const BellIcon = FaBell as React.ComponentType
   const SunIcon = CiSun as React.ComponentType

   return (
      <div className={style.header}>
         <div className={style.headerTitle}>
            <img
               src="/img/avatar.webp"
               alt="Avatar"
               className={style.headerTitleAvatar}
            />
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
               <div className={style.leftTown}>Town</div>
               <div className={style.leftTemperature}>100C</div>
               <div className={style.leftWeather}>weather</div>
            </div>
            <div className={style.weatherCardRight}>
               <div className={style.rightWeatherIcon}>
                  <SunIcon />
               </div>
               <div className={style.rightPollen}>pollen: level</div>
               <div className={style.rightSource}>weather</div>
            </div>
         </div>
      </div>
   )
}

export default HeaderHome
