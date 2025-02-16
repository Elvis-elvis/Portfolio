import { createBrowserRouter } from 'react-router-dom';
import { PathRoutes } from './path.routes';
import WelcomePage from './pages/WelcomePage';
import CommentsPage from './pages/CommentsPage';
import UpdateProjectPage from "./pages/UpdateProjectPage";
import AddProjectPage from "./pages/AddProjectPage";
import AdminDashboardPage from "./pages/AdminDashboardPage";
import CallbackPage from "./pages/Callbackpage";

const router = createBrowserRouter([
  {
    path: PathRoutes.WelcomPage,
    element: <WelcomePage />,
  },
  {
    path: PathRoutes.CommentsPage,
    element: <CommentsPage />,
  },
  {
    path: PathRoutes.AddProjectPage,
    element: <AddProjectPage />,
  },
  {
    path: PathRoutes.UpdateProjectPage,
    element: <UpdateProjectPage />,
  },
  {
    path: PathRoutes.AdminDashboardPage,
    element: <AdminDashboardPage />,
  },
  {
    path: PathRoutes.Callbackpage,
    element: <CallbackPage />,
  }
]);

export default router;
