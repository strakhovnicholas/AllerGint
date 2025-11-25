import style from './HeaderProfile.module.css'

function HeaderProfile() {
   let username: string = 'User'

   return (
      <div className={style.header}>
         <div className={style.headerTitle}>
            <div className={style.headerTitleText}>
               <h2 className={style.headerTitleTitle}>
                  Добро пожаловать, {username}
               </h2>
               <h6 className={style.headerTitleDescription}>
                  Сегодня хороший день
               </h6>
            </div>
         </div>
      </div>
   )
}

export default HeaderProfile
