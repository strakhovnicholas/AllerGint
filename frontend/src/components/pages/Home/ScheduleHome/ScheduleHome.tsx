import style from './ScheduleHome.module.css'

function ScheduleHome() {
   return (
      <div className={style.body}>
         {/*TODO добавить перебор массива полученного из дневника*/}
         <div className={style.title}>Расписание на сегодня</div>

         <div className={style.task}>
            <img
               src="img/svg/clock.svg"
               alt="Лекарства"
               width="32px"
               height="32px"
               className={style.icon}
            />
         </div>
      </div>
   )
}

export default ScheduleHome
