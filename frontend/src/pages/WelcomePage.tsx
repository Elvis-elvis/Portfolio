import Footer from '../components/Footer';
import Welcome from '../components/Welcome';
import '../components/Welcome.css';


export default function WelcomePage(): JSX.Element {
  return (
    <div>
      <Welcome />
      <Footer/>
    </div>
  );
}

