import { Container } from './Container';
import Navigation from './navigation/Navigation';

interface LayoutProps {
  children: React.ReactNode;
}

export default function Layout({ children }: LayoutProps) {
  return (
    <div className="flex min-h-screen flex-col">
      <main className="flex-grow bg-gray-400">
        <Navigation />
          <Container>{children}</Container>
      </main>
    </div>
  );
}
