import './App.css';
import { RouterProvider } from 'react-router-dom';
import router from './router';
import 'bootstrap/dist/css/bootstrap.min.css';
import { I18nextProvider } from 'react-i18next';
import i18n from './i18n';
import { Suspense } from 'react';

function App() {
  return (
      <I18nextProvider i18n={i18n}>
        <Suspense fallback={<div>Loading translations...</div>}>
          <RouterProvider router={router} />
        </Suspense>
      </I18nextProvider>
  );
}
export default App;
