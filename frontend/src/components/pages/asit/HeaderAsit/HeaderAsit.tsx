import { FaSyringe } from 'react-icons/fa'
import style from './HeaderAsit.module.css'

function HeaderAsit() {
   const SyringeIcon = FaSyringe as React.ComponentType

   return (
      <div className={style.header}>
         <div className={style.headerTitle}>
            <div className={style.headerTitleText}>
               <h2 className={style.headerTitleTitle}>АСИТ терапия</h2>
               <h6 className={style.headerTitleDescription}>
                  Аллерген-специфическая иммунотерапия
               </h6>
            </div>
            <div className={style.headerTitleIcon}>
               <SyringeIcon />
            </div>
         </div>

         <div className={style.headerInfoCard}>
            <div className={style.infoCardLeft}>
               <div className={style.leftTitle}>Активных курсов</div>
               <div className={style.leftCount}>2</div>
               <div className={style.leftDescription}>текущих терапий</div>
            </div>
            <div className={style.infoCardRight}>
               <div className={style.rightTitle}>Следующая инъекция</div>
               <div className={style.rightDate}>20.01.2024</div>
               <div className={style.rightTime}>14:00</div>
            </div>
         </div>
      </div>
   )
}

export default HeaderAsit
