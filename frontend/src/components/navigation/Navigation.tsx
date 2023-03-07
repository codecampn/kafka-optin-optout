import { useRouter } from 'next/router';
import React from 'react';
import { Container } from '../Container';
import { NavItem } from './NavigationItem';
import { NavigatinLogo } from './NavigationLogo';

export type Route = {
  href: string;
  name: string;
};

const Routes: Route[] = [
  { href: '/optin', name: 'OptIn' },
  { href: '/optout', name: 'OptOut' },
  { href: '/ktable', name: 'KTable Overview' },
  { href: '/database', name: 'Database Overview' },
];

export default function Navigation() {
  const { pathname } = useRouter();

  return (
    <div className="bg-gray-800 align-baseline shadow-sm md:py-2">
      <Container as="nav" className="flex h-16 items-center">
        <NavigatinLogo />

        <ul className="flex space-x-16">
          {Routes.map((route, key) => (
            <NavItem
              key={key}
              href={route.href}
              name={route.name}
              pathname={pathname}
            />
          ))}
        </ul>
      </Container>
    </div>
  );
}
