import { useEffect, useState } from 'react'
import styles from "./Header.module.css"
import { Link, useLocation } from "react-router-dom"
import { getWeatherDescription } from '../../../utils/weatherCodes'

interface WeatherCard {
  town: string
  temp: number
  weather: string
  pollen: string
  source: string[]
}

function Header() {
  const location = useLocation()
  const isProfileActive = location.pathname === "/profile"
  const [weather, setWeather] = useState<WeatherCard | null>(null)

  useEffect(() => {
    navigator.geolocation.getCurrentPosition(
      position => {
        const lat = position.coords.latitude
        const lon = position.coords.longitude

        fetch(
          `https://api.open-meteo.com/v1/forecast?latitude=${lat}&longitude=${lon}&current_weather=true&timezone=auto`
        )
          .then(res => res.json())
          .then(data => {
            setWeather({
              town: data.timezone.split('/')[1] || 'Город',
              temp: data.current_weather.temperature,
              weather: getWeatherDescription(data.current_weather.weathercode),
              pollen: 'Низкий уровень пыльцы',
              source: ['Берёза', 'Дуб'],
            })
          })
          .catch(err => console.error('Ошибка при получении погоды:', err))
      },
      err => console.error('Ошибка геолокации:', err)
    )
  }, [])

  return (
    <header className={styles.header}>
      <div className={styles.headerWrapper}>
        <div className={styles.headerContent}>
          {/* Left section: Logo + Navigation */}
          <div className={styles.leftSection}>
            <Link to="/" className={styles.logoLink}>
              <div className={styles.logo}>
                <img src="/img/logo.png" alt="Health App" className={styles.logoIcon} />
              </div>
            </Link>

            <nav className={styles.nav}>
              <Link
                to="/notebook"
                className={
                  location.pathname === "/notebook" ? `${styles.navLink} ${styles.navLinkActive}` : styles.navLink
                }
              >
                Дневник
              </Link>
              <Link
                to="/map"
                className={location.pathname === "/map" ? `${styles.navLink} ${styles.navLinkActive}` : styles.navLink}
              >
                Карта
              </Link>
            </nav>
          </div>

          {weather && (
            <div className={styles.weatherSection}>
              <div className={styles.weatherInfo}>
                <div className={styles.weatherTemp}>{weather.temp}°C</div>
                <div className={styles.weatherTown}>{weather.town}</div>
              </div>
              <div className={styles.weatherDetails}>
                <div className={styles.weatherCondition}>{weather.weather}</div>
                <div className={styles.weatherPollen}>{weather.pollen}</div>
              </div>
            </div>
          )}

          <Link
            to="/profile"
            className={isProfileActive ? `${styles.profileButton} ${styles.profileButtonActive}` : styles.profileButton}
          >
            <img src="/img/svg/user.svg" alt="Профиль" className={styles.profileIcon} />
            <span className={styles.profileText}>Профиль</span>
          </Link>
        </div>
      </div>
    </header>
  )
}

export default Header