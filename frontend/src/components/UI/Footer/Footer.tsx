import { useState } from 'react'
import styles from './Footer.module.css'
import FooterIcon from './FooterIcon/FooterIcon'

function Footer() {
  const [activeIcon, setActiveIcon] = useState<string>('home')

  return (
    <div className={styles.footer}>
      <div className={styles.footerWrapper}>
        <div className={styles.footerWrapperList}>
          <div onClick={() => setActiveIcon('home')}>
            <FooterIcon
              link="/"
              svgPath="/img/svg/home.svg"
              text="Главная"
              isActive={activeIcon === 'home'}
            />
          </div>
          <div onClick={() => setActiveIcon('notebook')}>
            <FooterIcon
              link="/notebook"
              svgPath="/img/svg/book.svg"
              text="Дневник"
              isActive={activeIcon === 'notebook'}
            />
          </div>
          <div onClick={() => setActiveIcon('pils')}>
            <FooterIcon
              link="/pils"
              svgPath="/img/svg/pils.svg"
              text="Лекарства"
              isActive={activeIcon === 'pils'}
            />
          </div>
          <div onClick={() => setActiveIcon('map')}>
            <FooterIcon
              link="/map"
              svgPath="/img/svg/map.svg"
              text="Карта"
              isActive={activeIcon === 'map'}
            />
          </div>
          <div onClick={() => setActiveIcon('profile')}>
            <FooterIcon
              link="/profile"
              svgPath="/img/svg/user.svg"
              text="Профиль"
              isActive={activeIcon === 'profile'}
            />
          </div>
          {/* <li className={styles.footerWrapperListItem}>
            <a href="">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="22.5px"
                height="20px"
                viewBox="0 0 576 512"
              >
                <path
                  fill="  "
                  d="M575.8 255.5c0 18-15 32.1-32 32.1l-32 0 .7 160.2c0 2.7-.2 5.4-.5 8.1l0 16.2c0 22.1-17.9 40-40 40l-16 0c-1.1 0-2.2 0-3.3-.1c-1.4 .1-2.8 .1-4.2 .1L416 512l-24 0c-22.1 0-40-17.9-40-40l0-24 0-64c0-17.7-14.3-32-32-32l-64 0c-17.7 0-32 14.3-32 32l0 64 0 24c0 22.1-17.9 40-40 40l-24 0-31.9 0c-1.5 0-3-.1-4.5-.2c-1.2 .1-2.4 .2-3.6 .2l-16 0c-22.1 0-40-17.9-40-40l0-112c0-.9 0-1.9 .1-2.8l0-69.7-32 0c-18 0-32-14-32-32.1c0-9 3-17 10-24L266.4 8c7-7 15-8 22-8s15 2 21 7L564.8 231.5c8 7 12 15 11 24z"
                />
              </svg>
              <p>Главная</p>
            </a>
          </li>
          <li className={styles.footer_wrapper_listItem}>
            <a href="">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 640 640"
                width="22.5px"
                height="20px"
              >
                <path
                  fill="currentColor"
                  d="M480 576L192 576C139 576 96 533 96 480L96 160C96 107 139 64 192 64L496 64C522.5 64 544 85.5 544 112L544 400C544 420.9 530.6 438.7 512 445.3L512 512C529.7 512 544 526.3 544 544C544 561.7 529.7 576 512 576L480 576zM192 448C174.3 448 160 462.3 160 480C160 497.7 174.3 512 192 512L448 512L448 448L192 448zM224 216C224 229.3 234.7 240 248 240L424 240C437.3 240 448 229.3 448 216C448 202.7 437.3 192 424 192L248 192C234.7 192 224 202.7 224 216zM248 288C234.7 288 224 298.7 224 312C224 325.3 234.7 336 248 336L424 336C437.3 336 448 325.3 448 312C448 298.7 437.3 288 424 288L248 288z"
                />
              </svg>
              <p>Дневник</p>
            </a>
          </li>
          <li className={styles.footer_wrapper_listItem}>
            <a href="">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 640 640"
                width="22.5px"
                height="20px"
              >
                <path
                  fill="currentColor"
                  d="M128 176C128 149.5 149.5 128 176 128C202.5 128 224 149.5 224 176L224 288L128 288L128 176zM240 432C240 383.3 258.1 338.8 288 305L288 176C288 114.1 237.9 64 176 64C114.1 64 64 114.1 64 176L64 464C64 525.9 114.1 576 176 576C213.3 576 246.3 557.8 266.7 529.7C249.7 501.1 240 467.7 240 432zM304.7 499.4C309.3 508.1 321 509.1 328 502.1L502.1 328C509.1 321 508.1 309.3 499.4 304.7C479.3 294 456.4 288 432 288C352.5 288 288 352.5 288 432C288 456.3 294 479.3 304.7 499.4zM361.9 536C354.9 543 355.9 554.7 364.6 559.3C384.7 570 407.6 576 432 576C511.5 576 576 511.5 576 432C576 407.7 570 384.7 559.3 364.6C554.7 355.9 543 354.9 536 361.9L361.9 536z"
                />
              </svg>
              <p>Таблетки</p>
            </a>
          </li>

          <li className={styles.footer_wrapper_listItem}>
            <a href="">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 640 640"
                width="22.5px"
                height="20px"
              >
                <path
                  fill="currentColor"
                  d="M576 112C576 100.9 570.3 90.6 560.8 84.8C551.3 79 539.6 78.4 529.7 83.4L413.5 141.5L234.1 81.6C226 78.9 217.3 79.5 209.7 83.3L81.7 147.3C70.8 152.8 64 163.9 64 176L64 528C64 539.1 69.7 549.4 79.2 555.2C88.7 561 100.4 561.6 110.3 556.6L226.4 498.5L405.8 558.3C413.9 561 422.6 560.4 430.2 556.6L558.2 492.6C569 487.2 575.9 476.1 575.9 464L575.9 112zM256 440.9L256 156.4L384 199.1L384 483.6L256 440.9z"
                />
              </svg>
              <p>Карта</p>
            </a>
          </li>

          <li className={styles.footer_wrapper_listItem}>
            <a href="">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 640 640"
                width="22.5px"
                height="20px"
              >
                <path
                  fill="currentColor"
                  d="M320 312C386.3 312 440 258.3 440 192C440 125.7 386.3 72 320 72C253.7 72 200 125.7 200 192C200 258.3 253.7 312 320 312zM290.3 368C191.8 368 112 447.8 112 546.3C112 562.7 125.3 576 141.7 576L498.3 576C514.7 576 528 562.7 528 546.3C528 447.8 448.2 368 349.7 368L290.3 368z"
                />
              </svg>
              <p>Профиль</p>
            </a>
          </li> */}
        </div>
      </div>
    </div>
  )
}

export default Footer
