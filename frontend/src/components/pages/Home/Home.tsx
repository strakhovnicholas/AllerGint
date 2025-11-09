import ActionsHome from './ActionsHome/ActionsHome'
import HeaderHome from './HeaderHome/HeaderHome'
import style from './Home.module.css'
import ScheduleHome from './ScheduleHome/ScheduleHome'

function Home() {
   return (
      <div>
         <HeaderHome />
         <div className={style.actions}>
            <ActionsHome />
         </div>
         <div className={style.schedule}>
            <ScheduleHome />
         </div>
      </div>
   )
}

export default Home
