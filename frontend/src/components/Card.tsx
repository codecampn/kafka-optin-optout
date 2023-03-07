import { ReactNode } from 'react';
import classnames from 'classnames';
export interface CardProps {
  id?: string;
  headline?: string;
  children: ReactNode;
  fullHeight?: boolean;
  className?: string;
}
const Headline = (headline: string) => {
  return (
    <h3 className="mb-2 text-lg font-semibold leading-6 text-gray-900 md:mb-3">
      {headline}
    </h3>
  );
};

export function Card({
  children,
  headline,
  fullHeight = false,
  className = '',
  id,
}: CardProps) {
  const cardClassNames = classnames(
    className,
    'shadow-md bg-white rounded-md',
    fullHeight ? 'h-full' : ''
  );

  return (
    <div id={id} className={cardClassNames}>
      <div className="divide-y-2 border-gray-200 px-4 py-5 sm:px-6">
        {headline && (
          <div>
            <div className="-ml-4 -mt-4 flex flex-wrap items-center justify-between sm:flex-nowrap">
              <div className="ml-4 mt-4">{Headline(headline)}</div>
            </div>
          </div>
        )}
        <div className="py-2 md:py-3">{children}</div>
      </div>
    </div>
  );
}
