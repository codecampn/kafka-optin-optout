import { Container } from '@/components/Container';
import { Card } from '@/components/Card';
import { OptInPhone } from '@/components/opt-in/OptInPhone';
import { OptInEmail } from '@/components/opt-in/OptInEmail';

export default function OptInPage() {
  return (
    <Container className="flew-row flex space-x-8">
      <Card headline="OptIn E-mail" className="mt-8">
        <OptInEmail />
      </Card>
      <Card headline="OptIn Phone" className="mt-8">
        <OptInPhone />
      </Card>
    </Container>
  );
}
