import style from './ConcentrationMap.module.css'

function ConcentrationMap() {
   return (
      <div className="card">
         <div className="title">Уровни концентрации пыльцы</div>
         <div className={style.wrapper}>
            <div className={style.item}>
               <div className={style.red}></div>Очень высокий (&#62;100
               зерен/м³)
            </div>
            <div className={style.item}>
               <div className={style.orange}></div>Высокий (50-100 зерен/м³)
            </div>
            <div className={style.item}>
               <div className={style.yellow}></div>Средний (20-50 зерен/м³)
            </div>
            <div className={style.item}>
               <div className={style.green}></div>Низкий (&#60;20 зерен/м³)
            </div>
         </div>
      </div>
   )
}

export default ConcentrationMap
