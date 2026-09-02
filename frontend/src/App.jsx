import React from 'react'

function App() {
  return (
    <div className="app">

      <header className="navbar">
        <h2>FoodHub</h2>

        <nav>
          <a href="#">Home</a>
          <a href="#restaurants">Restaurants</a>
          <a href="#orders">Orders</a>
          <button className="login-btn">Login</button>
        </nav>
      </header>

      <section className="hero">
        <div className="hero-content">
          <h1>Delicious food delivered to your door</h1>

          <p>
            Order from your favourite restaurants and enjoy fast delivery.
          </p>

          <div className="search-box">
            <input
              type="text"
              placeholder="Search restaurants or food..."
            />

            <button>Search</button>
          </div>
        </div>
      </section>

      <section id="restaurants" className="restaurants">

        <h2>Popular Restaurants</h2>

        <div className="restaurant-grid">

          <div className="card">
            <div className="food-image">🍕</div>

            <h3>Pizza Palace</h3>

            <p>Pizza • Italian</p>

            <div className="rating">
              ⭐ 4.5
            </div>

            <button>View Menu</button>
          </div>

          <div className="card">
            <div className="food-image">🍔</div>

            <h3>Burger House</h3>

            <p>Burgers • Fast Food</p>

            <div className="rating">
              ⭐ 4.4
            </div>

            <button>View Menu</button>
          </div>

          <div className="card">
            <div className="food-image">🍛</div>

            <h3>Spice Kitchen</h3>

            <p>Indian • Biryani</p>

            <div className="rating">
              ⭐ 4.7
            </div>

            <button>View Menu</button>
          </div>

        </div>

      </section>

      <section className="services">

        <h2>Why FoodHub?</h2>

        <div className="service-grid">

          <div>
            <h3>⚡ Fast Delivery</h3>
            <p>Food delivered quickly to your location.</p>
          </div>

          <div>
            <h3>🍽️ Great Restaurants</h3>
            <p>Choose from multiple restaurants and cuisines.</p>
          </div>

          <div>
            <h3>💳 Secure Payments</h3>
            <p>Easy and secure payment processing.</p>
          </div>

        </div>

      </section>

      <footer>
        <p>FoodHub Microservices Platform</p>
      </footer>

    </div>
  )
}

export default App
