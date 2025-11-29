import ActionsHome from './ActionsHome/ActionsHome'
import style from './Home.module.css'
import RecomendationsHome from './RecomendationsHome/RecomendationsHome'

function Home() {
    return (<div className={style.homeContainer}>
        <div className={style.recomendations}>
            <RecomendationsHome/>
        </div>
        <div className={style.actions}>
            <ActionsHome/>
        </div>
    </div>)
}

export default Home
