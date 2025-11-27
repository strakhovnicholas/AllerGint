import style from './ScheduleHome.module.css'

function ScheduleHome() {
   return (
      <div className="card">
         {/*TODO добавить перебор массива полученного из дневника*/}
         <div className="title">Расписание на сегодня</div>

         <div className={style.notification}>
            <div className={style.task}>
               <img
                  src="img/svg/clock.svg"
                  alt="Лекарства"
                  width="28px"
                  height="28px"
                  className={style.icon}
               />
            </div>
            <div className={style.notificationContent}>
               <div className={style.info}>
                  <span className={style.taskName}>taskName</span>
                  <span className={style.time}>time - status</span>
               </div>
            </div>
         </div>
      </div>
   )
}

export default ScheduleHome
