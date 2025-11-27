import React from 'react'
import style from './TherapyProfile.module.css'

function TherapyProfile() {
   // Пример данных - замените на реальные данные из вашего приложения
   const therapies = [
      {
         id: 1,
         name: 'Физиотерапия',
         date: '2024-01-15',
         doctor: 'Др. Иванов И.И.',
         status: 'active',
      },
      {
         id: 2,
         name: 'Массаж',
         date: '2024-01-10',
         doctor: 'Др. Петрова А.А.',
         status: 'completed',
      },
      {
         id: 3,
         name: 'ЛФК',
         date: '2024-01-20',
         doctor: 'Др. Сидоров С.С.',
         status: 'planned',
      },
   ]

   const getStatusClass = (status: string) => {
      switch (status) {
         case 'active':
            return style.statusActive
         case 'completed':
            return style.statusCompleted
         case 'planned':
            return style.statusPlanned
         default:
            return ''
      }
   }

   const getStatusText = (status: string) => {
      switch (status) {
         case 'active':
            return 'Активная'
         case 'completed':
            return 'Завершена'
         case 'planned':
            return 'Запланирована'
         default:
            return 'Неизвестно'
      }
   }

   return (
      <div className="card">
         <div className="title">Терапия</div>

         <div className={style.content}>
            {therapies.length > 0 ? (
               <div className={style.therapyList}>
                  {therapies.map((therapy) => (
                     <div key={therapy.id} className={style.therapyItem}>
                        <h3 className={style.therapyName}>{therapy.name}</h3>
                        <div className={style.therapyDetails}>
                           <div className={style.therapyDate}>
                              Дата: {therapy.date}
                           </div>
                           <div className={style.therapyDoctor}>
                              Врач: {therapy.doctor}
                           </div>
                        </div>
                        <div
                           className={`${style.status} ${getStatusClass(
                              therapy.status
                           )}`}
                        >
                           {getStatusText(therapy.status)}
                        </div>
                     </div>
                  ))}
               </div>
            ) : (
               <div className={style.infoRow}>
                  <span className={style.infoLabel}>Нет данных о терапии</span>
               </div>
            )}
         </div>
      </div>
   )
}

export default TherapyProfile
