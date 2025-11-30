import MedProfile from './MedProfile/MedProfile'
import style from './Profile.module.css'

function Profile() {
   return (
      <div>
         <div className={style.med}>
            <MedProfile />
         </div>
      </div>
   )
}

export default Profile
