import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext.jsx';
import './Navbar.css';

const Navbar = () => {
  const { isAuthenticated, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  if (!isAuthenticated) {
    return null;
  }

  return (
    <nav className="navbar">
      <div className="navbar-container">
        <Link to="/" className="navbar-brand">
          Task Manager
        </Link>
        <div className="navbar-menu">
          <Link to="/" className="navbar-link">
            Dashboard
          </Link>
          <button onClick={handleLogout} className="navbar-button">
            Logout
          </button>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;

